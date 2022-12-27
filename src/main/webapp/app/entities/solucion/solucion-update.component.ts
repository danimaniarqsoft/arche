import { Component, Vue, Inject } from 'vue-property-decorator';

import AlertService from '@/shared/alert/alert.service';

import { ISolucion, Solucion } from '@/shared/model/solucion.model';
import { Componente } from '@/shared/model/componente.model';
import { Menu } from '@/shared/model/menu.model';
import SolucionService from './solucion.service';

const validations: any = {
  solucion: {
    titulo: {},
    descripcion: {},
  },
};

@Component({
  validations,
})
export default class SolucionUpdate extends Vue {
  @Inject('solucionService') private solucionService: () => SolucionService;
  @Inject('alertService') private alertService: () => AlertService;

  public solucion: ISolucion = new Solucion();
  public forms: any = {};
  public isSaving = false;
  public options = { readOnly: false, languaje: 'en', viewAsHtml: false };
  public currentLanguage = '';
  public icon = 'fas fa-user-cog';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.solucionId) {
        vm.retrieveSolucion(to.params.solucionId);
      }
      vm.initRelationships();
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
  }

  public save(): void {
    this.isSaving = true;
    if (this.solucion.id) {
      this.solucionService()
        .update(this.solucion)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('archeApp.solucion.updated', { param: param.id });
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
      this.solucionService()
        .create(this.solucion)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('archeApp.solucion.created', { param: param.id });
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

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.solucionService()
      .retrieveForms()
      .then(res => {
        this.forms = res.data;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public handleAddComponente(iconSelected: string, form: any): void {
    const componente = new Componente();
    componente.formId = form._id;
    componente.titulo = form.title;
    componente.descripcion = form.name;
    componente.orden = this.solucion.componentes.length + 1;
    componente.icon = iconSelected;
    this.solucion.componentes.push(componente);
    this.emitComponent(this.solucion.componentes);
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
    this.emitComponent(this.solucion.componentes);
  }

  public isInComponents(form: any): boolean {
    return this.solucion.componentes.find(comp => comp.formId === form._id) ? true : false;
  }

  public emitComponent(componentes: any) {
    this.$root.$emit('menu', componentes);
  }
}
