import { mixins } from 'vue-class-component';

import FormsHandler from '@/components/forms/forms-handler';
import { Component, Inject } from 'vue-property-decorator';
import { ISolicitud, Solicitud } from '@/shared/model/solicitud.model';
import { EstadoSolicitud } from '@/shared/model/enumerations/estado-solicitud.model';
import { StatusCodes } from 'http-status-codes';
import SolicitudService from './solicitud.service';

const validations: any = {
  solicitud: {
    nombre: {},
  },
};

@Component({
  validations,
})
export default class SolicitudUpdate extends mixins(FormsHandler) {
  @Inject('solicitudService') public solicitudService: () => SolicitudService;
  public currentLanguage = '';
  public solicitud: ISolicitud = new Solicitud();

  public filter = { currentSolicitudId: null, currentSolucionId: null };
  public executed = false;
  public errores = [];

  public toggle() {
    this.executed = !this.executed;
    return this.executed;
  }
  beforeRouteEnter(to, from, next) {
    next(vm => {
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
        this.redrawForm();
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
      .then(solicitud => {
        this.solicitud = solicitud;
        this.isSaving = false;
        this.retrieveSolucion(this.solicitud.solucionId);
        const message = this.$t('archeApp.solicitud.created', { param: solicitud.id });
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

  public handleFormLoad(): void {
    if (this.solicitud.id) {
      this.solicitudService()
        .find(this.solicitud.id)
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
    this.save({ ...this.solicitud, ...submit[1].data });
  }

  public handleSend(): void {
    this.isSaving = true;
    this.solicitudService()
      .send(this.solicitud)
      .then(solicitud => {
        this.isSaving = false;
        this.solicitud = solicitud;
        const message = this.$t('archeApp.solicitud.updated', { param: solicitud.id });
        this.infoMessage(message.toString());
      })
      .catch(error => {
        this.isSaving = false;
        console.log(error.response);
        if (error.response.data.status === StatusCodes.PRECONDITION_FAILED) {
          this.errores = error.response.data.errores;
        } else {
          this.alertService().showHttpError(this, error.response);
        }
      });
  }
  public previousState(): void {
    this.$router.go(-1);
  }

  get isSolicitudSent(): boolean {
    return this.solicitud.estado === EstadoSolicitud.ENVIADA;
  }

  public isReadOnly(): boolean {
    console.log(this.solicitud.estado);
    return this.solicitud.estado === EstadoSolicitud.ENVIADA;
  }
}
