import { Component, Vue, Inject } from 'vue-property-decorator';
import { ISolicitud, Solicitud } from '@/shared/model/solicitud.model';
import { ISolucion, Solucion } from '@/shared/model/solucion.model';

import SolicitudService from './solicitud.service';
import SolucionService from '@/entities/solucion/solucion.service';

import AlertService from '@/shared/alert/alert.service';

@Component
export default class FormHandler extends Vue {
  @Inject('solicitudService') private solicitudService: () => SolicitudService;
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('solucionService') private solucionService: () => SolucionService;

  public form: any = {};
  public options = { readOnly: false, languaje: 'en', viewAsHtml: false };
  public context: any = { submission: { data: {} } };
  public solicitudId = null;
  public solicitud: ISolicitud = new Solicitud();
  public solucion: ISolucion = new Solucion();

  public isSaving = false;
  public currentLanguage = '';
  public isFetching = false;

  get isFormEmpty() {
    return Object.keys(this.form).length === 0;
  }

  public retriveForm(context: any): void {
    this.solicitudService()
      .retrieveForms(context.currentComponente.formId)
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
          this.context.submission = { data: this.solicitud };
        })
        .catch(error => {
          this.alertService().showHttpError(this, error.response);
        });
    }
  }

  public save(solicitud: ISolicitud): void {
    this.isSaving = true;
    if (solicitud.id) {
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
    } else {
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

  public retrieveSolucion(solucionId): void {
    this.solucionService()
      .find(solucionId)
      .then(res => {
        this.solucion = res;
        (this.$root as any).$emit('show-side-navbar', true);
        this.emitComponent(this.solucion.componentes);
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }
  public handleSubmit(submit): void {
    this.solicitud = submit[1].data;
    this.save(this.solicitud);
  }

  public emitComponent(componentes: any) {
    console.log(componentes);

    (this.$root as any).$emit('menu', componentes);
  }
}
