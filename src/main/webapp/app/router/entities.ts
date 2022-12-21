import { Authority } from '@/shared/security/authority';
/* tslint:disable */
// prettier-ignore
const Entities = () => import('@/entities/entities.vue');

// prettier-ignore
const Solicitud = () => import('@/entities/solicitud/solicitud.vue');
// prettier-ignore
const SolicitudUpdate = () => import('@/entities/solicitud/solicitud-update.vue');

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
      path: 'solicitud/:solicitudId/view',
      name: 'SolicitudView',
      component: SolicitudUpdate,
      meta: { authorities: [Authority.USER], readOnly: true },
    },
    // jhipster-needle-add-entity-to-router - JHipster will add entities to the router here
  ],
};
