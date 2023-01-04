import { Component, Vue, Inject } from 'vue-property-decorator';
import { ISolucion, Solucion } from '@/shared/model/solucion.model';
import { Menu } from '@/shared/model/menu.model';

import SolucionService from '@/entities/solucion/solucion.service';
import FormService from '@/shared/form/form.service';
import SendSolicitudComponent from '@/components/forms/send-solicitud.vue';
import { i18n } from '@/components/forms/i18n';

import AlertService from '@/shared/alert/alert.service';

@Component({
  components: {
    'send-solicitud': SendSolicitudComponent,
  },
})
export default class FormHandler extends Vue {
  @Inject('alertService') public alertService: () => AlertService;
  @Inject('formService') public formService: () => FormService;
  @Inject('solucionService') public solucionService: () => SolucionService;

  public form: any = {};
  public options = { readOnly: false, languaje: 'es', viewAsHtml: false, i18n: i18n };
  public formContext: any = { submission: { data: {} } };

  public solucion: ISolucion = new Solucion();

  public isSaving = false;
  public currentLanguage = '';
  public isFetching = false;
  public isSendVisible = false;
  public isFormioVisible = false;
  public formioKey = 0;

  mounted() {
    (this.$root as any).$on('load-form', componente => {
      if (componente.tipo && componente.tipo === 'send') {
        this.isSendVisible = true;
        this.isFormioVisible = false;
      } else {
        this.isFormioVisible = true;
        this.isSendVisible = false;
        this.formContext.currentComponente = componente;
        this.retriveForm(this.formContext);
      }
    });
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
            this.options.readOnly = true;
          }
          this.form = resForm.data;
          this.formioRerender();
        },
        err => {
          this.alertService().showHttpError(this, err.response);
        }
      );
  }

  public retrieveSolucion(solucionId): void {
    this.solucionService()
      .find(solucionId)
      .then(res => {
        this.solucion = res;
        this.showSideNavbar(true);
        this.updateDefaultMenu(this.solucion);
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
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

  public isReadOnly(): boolean {
    return false;
  }
}
