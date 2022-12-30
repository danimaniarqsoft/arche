import { mixins } from 'vue-class-component';

import FormHandler from '@/entities/solicitud/form-handler';
import { Component, Inject } from 'vue-property-decorator';
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
export default class SolicitudUpdate extends mixins(FormHandler) {
  @Inject('solicitudService') public solicitudService: () => SolicitudService;
  public currentLanguage = '';
  public solicitudId = null;
  public solicitud: ISolicitud = new Solicitud();

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.solicitudId) {
        vm.options.readOnly = to.meta.readOnly;
        vm.retrieveSolicitud(to.params.solicitudId);
      }
    });
  }

  beforeRouteLeave(to, from, next) {
    (this.$root as any).$emit('show-side-navbar', false);
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
  }

  public retrieveSolicitud(solicitudId): void {
    this.solicitudId = solicitudId;
    if (this.solicitudId) {
      this.solicitudService()
        .find(this.solicitudId)
        .then(res => {
          this.solicitud = res;
          this.retrieveSolucion(this.solicitud.solucionId);
        })
        .catch(error => {
          this.alertService().showHttpError(this, error.response);
        });
    }
  }

  public handleFormLoad(): void {
    if (this.solicitudId) {
      this.solicitudService()
        .find(this.solicitudId)
        .then(res => {
          this.solicitud = res;
          this.formContext.submission = { data: this.solicitud };
        })
        .catch(error => {
          this.alertService().showHttpError(this, error.response);
        });
    }
  }

  public handleSubmit(submit): void {
    this.solicitud = submit[1].data;
    this.save(this.solicitud);
  }

  public save(solicitud: ISolicitud): void {
    this.isSaving = true;
    if (solicitud.id) {
      this.updateSolicitud(solicitud);
    } else {
      this.createSolicitud(solicitud);
    }
  }

  public updateSolicitud(solicitud: ISolicitud): void {
    this.solicitudService()
      .update(solicitud)
      .then(param => {
        this.isSaving = false;
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
  }
  public createSolicitud(solicitud: ISolicitud): void {
    this.solicitudService()
      .create(solicitud)
      .then(param => {
        this.isSaving = false;
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

  public handleSend(): void {
    console.log('enviando una solicitud');
  }
  public previousState(): void {
    this.$router.go(-1);
  }
}
