import { Component, Vue, Inject } from 'vue-property-decorator';

import { ISolucion } from '@/shared/model/solucion.model';
import SolucionService from './solucion.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class SolucionDetails extends Vue {
  @Inject('solucionService') private solucionService: () => SolucionService;
  @Inject('alertService') private alertService: () => AlertService;

  public solucion: ISolucion = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.solucionId) {
        vm.retrieveSolucion(to.params.solucionId);
      }
    });
  }

  public retrieveSolucion(solucionId) {
    this.solucionService()
      .find(solucionId)
      .then(res => {
        this.solucion = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
