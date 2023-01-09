import { Authority } from '@/shared/security/authority';
/* tslint:disable */
// prettier-ignore
const Entities = () => import('@/entities/entities.vue');

// prettier-ignore
const Solicitud = () => import('@/entities/solicitud/solicitud.vue');
// prettier-ignore
const SolicitudUpdate = () => import('@/entities/solicitud/solicitud-update.vue');

// prettier-ignore
const Solucion = () => import('@/entities/solucion/solucion.vue');
// prettier-ignore
const SolucionUpdate = () => import('@/entities/solucion/solucion-update.vue');
// prettier-ignore
const SolucionDetails = () => import('@/entities/solucion/solucion-details.vue');
// prettier-ignore
const Convocatoria = () => import('@/entities/convocatoria/convocatoria.vue');
// jhipster-needle-add-entity-to-router-import - JHipster will import entities to the router here

export default {
  path: '/',
  component: Entities,
  children: [
    {
      path: 'solicitud',
      name: 'Solicitud',
      component: Solicitud,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'solicitud/new',
      name: 'SolicitudCreate',
      component: SolicitudUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'solicitud/:solicitudId/edit',
      name: 'SolicitudEdit',
      component: SolicitudUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'solicitud/:solucionId/edit',
      name: 'SolicitudCreateFromSolucion',
      component: SolicitudUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'solicitud/:solicitudId/view',
      name: 'SolicitudView',
      component: SolicitudUpdate,
      meta: { authorities: [Authority.USER], readOnly: true },
    },
    {
      path: 'solucion',
      name: 'Solucion',
      component: Solucion,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'solucion/new',
      name: 'SolucionCreate',
      component: SolucionUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'solucion/:solucionId/edit',
      name: 'SolucionEdit',
      component: SolucionUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'solucion/:solucionId/view',
      name: 'SolucionView',
      component: SolucionDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'convocatorias',
      name: 'Convocatoria',
      component: Convocatoria,
      meta: { authorities: [Authority.USER] },
    },
    // jhipster-needle-add-entity-to-router - JHipster will add entities to the router here
  ],
};
