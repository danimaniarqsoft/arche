import { Component, Vue, Inject } from 'vue-property-decorator';

import AlertService from '@/shared/alert/alert.service';

import { ISolicitud, Solicitud } from '@/shared/model/solicitud.model';
import SolicitudService from './solicitud.service';

const validations: any = {
  solicitud: {
    nombre: {},
  },
};

@Component({
  validations,
})
export default class SolicitudUpdate extends Vue {
  @Inject('solicitudService') private solicitudService: () => SolicitudService;
  @Inject('alertService') private alertService: () => AlertService;

  public solicitud: ISolicitud = new Solicitud();
  public isSaving = false;
  public currentLanguage = '';
  public options = { readOnly: false, languaje: 'en', viewAsHtml: false };
  public form: any = {};
  public isFetching = false;
  public solicitudId = null;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.solicitudId) {
        vm.retrieveSolicitud(to.params.solicitudId);
      }
    });
  }

  created(): void {
    this.currentLanguage = this.$store.getters.currentLanguage;
    this.$store.watch(
      () => this.$store.getters.currentLanguage,
      () => {
        this.currentLanguage = this.$store.getters.currentLanguage;
      }
    );
  }

  public enviar(forms) {
    console.log('estamos enviando algo!');
    console.log(forms);
    //    (<any>this.$refs.formioForm).formio.submit();
    //console.log(<any>this.$refs.formioForm);
  }

  public onChange(schema) {
    console.log('onChange');
    console.log(schema);
  }

  public save(): void {
    this.isSaving = true;
    if (this.solicitud.id) {
      this.solicitudService()
        .update(this.solicitud)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('archeApp.solicitud.updated', { param: param.id });
          return (this.$root as any).$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        })
        .catch(error => {
          this.isSaving = false;
          this.alertService().showHttpError(this, error.response);
        });
    } else {
      this.solicitudService()
        .create(this.solicitud)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('archeApp.solicitud.created', { param: param.id });
          (this.$root as any).$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Success',
            variant: 'success',
            solid: true,
            autoHideDelay: 5000,
          });
        })
        .catch(error => {
          this.isSaving = false;
          this.alertService().showHttpError(this, error.response);
        });
    }
  }

  public retrieveSolicitud(solicitudId): void {
    this.solicitudId = solicitudId;
    this.solicitudService()
      .retrieveForms()
      .then(
        resForm => {
          const cform = resForm.data;
          this.form = cform;
        },
        err => {
          this.alertService().showHttpError(this, err.response);
        }
      );
  }

  public handleFormLoad(): void {
    this.solicitudService()
      .find(this.solicitudId)
      .then(res => {
        this.solicitud = res;
        (<any>this.$refs.formioForm).submission = { data: this.solicitud };
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public handleSubmit(submit): void {
    this.solicitud = submit[1].data;
    this.save();
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {}
}
