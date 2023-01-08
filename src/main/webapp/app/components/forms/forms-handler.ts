import { Component, Vue, Inject } from 'vue-property-decorator';
import { ISolucion, Solucion } from '@/shared/model/solucion.model';
import { Menu } from '@/shared/model/menu.model';

import SolucionService from '@/entities/solucion/solucion.service';
import FormService from '@/shared/form/form.service';
import SendSolicitudComponent from '@/components/forms/send-solicitud/send-solicitud.vue';
import { DefaultFormOptions, DefaultBuilderOptions } from '@/components/forms/default-form-options';

import AlertService from '@/shared/alert/alert.service';

@Component({
  components: {
    'send-solicitud': SendSolicitudComponent,
  },
})
export default class FormsHandler extends Vue {
  @Inject('alertService') public alertService: () => AlertService;
  @Inject('formService') public formService: () => FormService;
  @Inject('solucionService') public solucionService: () => SolucionService;

  public form: any = {};
  public formOptions = new DefaultFormOptions();
  public builderOptions = new DefaultBuilderOptions();

  public formContext: any = { submission: { data: {} } };

  public solucion: ISolucion = new Solucion();

  public isSaving = false;
  public currentLanguage = '';
  public isFetching = false;
  public isSendVisible = false;
  public isFormioVisible = false;
  public formioKey = 0;
  public formioBuilderKey = 0;
  public isSent = false;

  mounted() {
    (this.$root as any).$on('load-form', this.handleLoadForms);
  }

  beforeDestroy() {
    (this.$root as any).$off('load-form', this.handleLoadForms);
  }

  public handleLoadForms(componente) {
    if (this.isLoadFormActivated()) {
      this.renderComponente(componente);
    }
  }

  public renderComponente(componente) {
    if (componente.tipo && componente.tipo === 'send') {
      this.isSendVisible = true;
      this.isFormioVisible = false;
    } else {
      this.isSendVisible = false;
      this.isFormioVisible = true;
      this.formContext.currentComponente = componente;
      this.retriveForm(this.formContext);
    }
  }

  public isLoadFormActivated(): boolean {
    return true;
  }

  get showWelcomeMessage() {
    return Object.keys(this.form).length === 0 && !this.isSendVisible;
  }

  public retriveForm(formContext: any): void {
    this.formService()
      .retrieveFormById(formContext.currentComponente.formId)
      .then(
        resForm => {
          if (this.isReadOnly()) {
            this.formOptions.readOnly = true;
          }
          this.form = resForm.data;
          this.formioRerender();
        },
        err => {
          this.alertService().showHttpError(this, err.response);
        }
      );
  }

  public retriveAllForms(): void {
    this.formService()
      .retrieveAllForms()
      .then(
        res => {
          this.doAfterRetriveAllForms(
            res.data.filter((value, index, arr) => {
              return !['userLogin', 'userRegister'].includes(value.name);
            })
          );
        },
        err => {
          this.alertService().showHttpError(this, err.response);
        }
      );
  }

  public doAfterRetriveAllForms(forms: any): void {}

  public retrieveSolucion(solucionId): void {
    this.solucionService()
      .find(solucionId)
      .then(res => {
        this.doAfterRetriveSolucion(res);
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  /**
   * Execute after to retrive a Solution. This method can be overrided by a client
   * This method is part of the {@link core-library#Statistics | Statistics subsystem}.
   *
   * @param solution - the {@link Solution }. retrived
   *
   */
  public doAfterRetriveSolucion(solucion: ISolucion): void {
    this.solucion = solucion;
    this.showSideNavbar(true);
    this.updateDefaultMenu(solucion);
  }

  public updateDefaultMenu(solucion: any) {
    const menu = new Menu();
    menu.isSendActivated = true;
    menu.isPreviewActivated = false;
    menu.componentes = solucion.componentes;
    (this.$root as any).$emit('update-menu', menu);
  }

  public showSideNavbar(show: boolean) {
    (this.$root as any).$emit('show-side-navbar', show);
  }

  public handleSubmit(submit): void {}

  public formioRerender(): void {
    this.formioKey += 1;
  }

  public builderRerender(): void {
    this.formioBuilderKey += 1;
  }

  public isReadOnly(): boolean {
    return false;
  }

  public redrawForm() {
    if (this.$refs.formio) {
      (this.$refs.formio as any).formio.redraw();
    }
  }

  public handleSend(): void {
    this.isSaving = true;
    setTimeout(() => {
      this.isSaving = false;
      this.isSent = true;
      const message = this.$t('archeApp.solicitud.updated', { param: 'test' });
      this.infoMessage(message.toString());
    }, 2000);
  }

  public infoMessage(message: string): void {
    this.message(message, 'Info', 'info');
  }

  public successMessage(message: string): void {
    this.message(message, 'Success', 'success');
  }

  public message(message: string, title: string, variant: string): void {
    (this.$root as any).$bvToast.toast(message, {
      toaster: 'b-toaster-top-center',
      title: title,
      variant: variant,
      solid: true,
      autoHideDelay: 5000,
    });
  }
}
