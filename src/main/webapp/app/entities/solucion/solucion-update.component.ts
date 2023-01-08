import { Component, Vue, Inject } from 'vue-property-decorator';

import { ISolucion, Solucion, Mensaje } from '@/shared/model/solucion.model';
import { Componente } from '@/shared/model/componente.model';
import { Menu } from '@/shared/model/menu.model';
import { EstadoSolucion } from '@/shared/model/enumerations/estado-solucion.model';
import SolucionService from './solucion.service';
import { mixins } from 'vue-class-component';
import FormsHandler from '@/components/forms/forms-handler';

const validations: any = {
  solucion: {
    titulo: {},
    descripcion: {},
    estado: {},
    mensaje: {
      bienvenida: {},
      terminos: {},
    },
    tags: {},
  },
};

@Component({
  validations,
})
export default class SolucionUpdate extends mixins(FormsHandler) {
  @Inject('solucionService') public solucionService: () => SolucionService;

  public solucion: ISolucion = new Solucion();
  public forms: any = {};
  public isPublishing = false;
  public currentLanguage = '';
  public estadoSolucionValues: string[] = Object.keys(EstadoSolucion);
  public icon = 'fas fa-user-cog';
  public isPreview = false;
  public cuestionario = { display: 'form' };
  public tabIndex = 0;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.solucionId) {
        vm.retrieveSolucion(to.params.solucionId);
      }
      vm.initRelationships();
      vm.$root.$emit('show-side-navbar', true);
    });
  }

  beforeRouteLeave(to, from, next) {
    (this.$root as any).$emit('show-side-navbar', false);
    next();
  }

  mounted() {
    (this.$root as any).$on('is-preview-activated', this.handleIsPreviewActivated);
  }

  beforeDestroy() {
    (this.$root as any).$off('is-preview-activated', this.handleIsPreviewActivated);
  }

  created(): void {
    this.currentLanguage = this.$store.getters.currentLanguage;
    this.$store.watch(
      () => this.$store.getters.currentLanguage,
      () => {
        this.currentLanguage = this.$store.getters.currentLanguage;
      }
    );
    this.builderRerender();
  }

  public handleIsPreviewActivated(activate: boolean) {
    this.isPreview = activate;
    this.isSendVisible = false;
    this.isSent = false;
    this.form = {};
  }

  public linkClass(idx) {
    if (this.tabIndex === idx) {
      return ['bg-primary', 'text-light'];
    } else {
      return ['bg-light'];
    }
  }
  public isLoadFormActivated(): boolean {
    return this.isPreview;
  }

  public handlePublicarConfirmation() {
    this.isPublishing = true;
    this.saveSolucion(EstadoSolucion.PUBLICADA);
  }

  public handleSave(): void {
    this.isSaving = true;
    this.saveSolucion(EstadoSolucion.EN_CAPTURA);
  }

  public handleAddComponente(iconSelected: string, form: any): void {
    const componente = new Componente();
    componente.formId = form._id;
    componente.titulo = form.title;
    componente.descripcion = form.name;
    componente.orden = this.solucion.componentes.length + 1;
    componente.icon = iconSelected;
    componente.path = form.path;
    this.solucion.componentes.push(componente);
    this.updateMenu(this.solucion);
  }

  public handleRemoveComponente(form: any): void {
    const componentes = this.solucion.componentes.filter((value, index, arr) => {
      if (value.formId === form._id) {
        arr.splice(index, 1);
        return true;
      }
      return false;
    });
    let index = 1;
    this.solucion.componentes.forEach(component => {
      component.orden = index++;
    });
    this.updateMenu(this.solucion);
  }

  public saveSolucion(estado: EstadoSolucion): void {
    this.solucion.estado = estado;
    this.solucion.cuestionario = this.cuestionario;
    if (this.solucion.id) {
      this.solucionService()
        .update(this.solucion)
        .then(param => {
          this.isSaving = false;
          this.isPublishing = false;
          const message = this.$t('archeApp.solucion.updated', { param: param.id });
          this.infoMessage(message.toString());
        })
        .catch(error => {
          this.isSaving = false;
          this.isPublishing = false;
          this.alertService().showHttpError(this, error.response);
        });
    } else {
      this.solucion.estado = EstadoSolucion.EN_CAPTURA;
      this.solucionService()
        .create(this.solucion)
        .then(param => {
          this.isSaving = false;
          this.isPublishing = false;
          const message = this.$t('archeApp.solucion.created', { param: param.id });
          this.successMessage(message.toString());
        })
        .catch(error => {
          this.isSaving = false;
          this.isPublishing = false;
          this.alertService().showHttpError(this, error.response);
        });
    }
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.retriveAllForms();
  }

  public isFormSelected(form: any): boolean {
    return this.solucion.componentes.find(comp => comp.formId === form._id) ? true : false;
  }

  public updateMenu(solucion: any) {
    const menu = new Menu();
    menu.isSendActivated = true;
    menu.isPreviewActivated = true;
    menu.componentes = solucion.componentes;
    (this.$root as any).$emit('update-menu', menu);
  }

  /** @override */
  public doAfterRetriveSolucion(solucion: ISolucion): void {
    this.solucion = solucion;
    this.solucion.mensaje = this.solucion.mensaje ? this.solucion.mensaje : new Mensaje();
    this.solucion.cuestionario = this.solucion.cuestionario ? this.solucion.cuestionario : { display: 'form', components: [] };
    this.cuestionario = this.solucion.cuestionario;
    this.updateMenu(this.solucion);
  }

  /** @override */
  public doAfterRetriveAllForms(forms: any): void {
    this.forms = forms;
  }
}
