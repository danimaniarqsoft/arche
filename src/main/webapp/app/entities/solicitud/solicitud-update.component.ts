import { Component } from 'vue-property-decorator';
import { mixins } from 'vue-class-component';

import FormHandler from '@/entities/solicitud/form-handler';

const validations: any = {
  solicitud: {
    nombre: {},
  },
};

@Component({
  validations,
})
export default class SolicitudUpdate extends mixins(FormHandler) {
  public currentLanguage = '';

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

  mounted() {
    (this.$root as any).$on('loadForm', componente => {
      this.context.currentComponente = componente;
      this.retriveForm(this.context);
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

  public previousState(): void {
    this.$router.go(-1);
  }
}
