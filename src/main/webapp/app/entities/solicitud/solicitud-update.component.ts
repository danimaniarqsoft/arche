import { Component, Vue, Inject } from 'vue-property-decorator';

import AlertService from '@/shared/alert/alert.service';

import { ISolucion, Solucion } from '@/shared/model/solucion.model';
import { ISolicitud, Solicitud } from '@/shared/model/solicitud.model';
import SolicitudService from './solicitud.service';
import SolucionService from '@/entities/solucion/solucion.service';

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
  @Inject('solucionService') private solucionService: () => SolucionService;

  @Inject('alertService') private alertService: () => AlertService;

  public solicitud: ISolicitud = new Solicitud();
  public solucion: ISolucion = new Solucion();
  public isSaving = false;
  public currentLanguage = '';
  public options = { readOnly: false, languaje: 'en', viewAsHtml: false };
  public form: any = {};
  public isFetching = false;
  public solicitudId = null;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.solicitudId) {
        vm.options.readOnly = to.meta.readOnly;
        vm.retrieveSolucion(to.params.solicitudId);
        //vm.retrieveSolicitud(to.params.solicitudId);
      }
      vm.$root.$emit('set-visible', true);
    });
  }

  beforeRouteLeave(to, from, next) {
    this.$root.$emit('off-visible', true);
    next();
  }

  created(): void {
    this.currentLanguage = this.$store.getters.currentLanguage;
    this.$store.watch(
      () => this.$store.getters.currentLanguage,
      () => {
        this.currentLanguage = this.$store.getters.currentLanguage;
      }
    );
    this.retriveForm();
  }

  public retrieveSolucion(solucionId): void {
    this.solucionService()
      .find(solucionId)
      .then(res => {
        this.solucion = res;
        this.emitComponent(this.solucion.componentes);
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
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
  }

  public retriveForm(): void {
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
    if (this.solicitudId) {
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
  }

  public handleSubmit(submit): void {
    this.solicitud = submit[1].data;
    this.save();
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {}
  public emitComponent(componentes: any) {
    this.$root.$emit('menu', componentes);
  }
}
