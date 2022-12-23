import { Component, Provide, Vue } from 'vue-property-decorator';

import UserService from '@/entities/user/user.service';
import SolicitudService from './solicitud/solicitud.service';
import SolucionService from './solucion/solucion.service';
// jhipster-needle-add-entity-service-to-entities-component-import - JHipster will import entities services here

@Component
export default class Entities extends Vue {
  @Provide('userService') private userService = () => new UserService();
  @Provide('solicitudService') private solicitudService = () => new SolicitudService();
  @Provide('solucionService') private solucionService = () => new SolucionService();
  // jhipster-needle-add-entity-service-to-entities-component - JHipster will import entities services here
}
