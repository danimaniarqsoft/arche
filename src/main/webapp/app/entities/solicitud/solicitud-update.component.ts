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

  public filter = { currentSolicitudId: null, currentSolucionId: null };
  public executed = false;

  public toggle() {
    this.executed = !this.executed;
    return this.executed;
  }
  beforeRouteEnter(to, from, next) {
    next(vm => {
      vm.options.readOnly = to.meta.readOnly;
      if (to.params.solicitudId) {
        vm.filter.currentSolicitudId = to.params.solicitudId;
      } else if (to.params.solucionId) {
        vm.filter.currentSolucionId = to.params.solucionId;
      }
      vm.retrieveSolicitud(vm.filter);
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

  public retrieveSolicitud(filter): void {
    this.filter = filter;
    if (this.filter.currentSolicitudId) {
      this.findById(this.filter.currentSolicitudId);
    } else if (this.filter.currentSolucionId) {
      this.findBySolucionIdAndCreateIfNotExist(this.filter.currentSolucionId);
    }
  }

  public findById(solicitudId): void {
    this.solicitudService()
      .find(solicitudId)
      .then(res => {
        this.solicitud = res;
        this.retrieveSolucion(this.solicitud.solucionId);
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public findBySolucionIdAndCreateIfNotExist(solucionId) {
    this.solicitudService()
      .findBySolucionId(solucionId)
      .then(res => {
        this.solicitud = res;
        this.retrieveSolucion(this.solicitud.solucionId);
      })
      .catch(error => {
        if (error.response.status === 404) {
          this.createSolicitudFromSolucion(solucionId);
        } else {
          this.alertService().showHttpError(this, error.response);
        }
      });
  }
  public createSolicitudFromSolucion(solucionId): void {
    this.solucionService()
      .find(solucionId)
      .then(res => {
        this.solucion = res;
        this.solicitud.nombre = this.solucion.titulo;
        this.solicitud.solucionId = this.solucion.id;
        this.save(this.solicitud);
        this.showSideNavbar(true);
        this.updateDefaultMenu(this.solucion);
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
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
    const tempo = submit[1].data;
    this.solicitud = { ...this.solicitud, tempo };
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
        this.retrieveSolucion(this.solicitud.solucionId);
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
