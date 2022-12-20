/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import SolicitudDetailComponent from '@/entities/solicitud/solicitud-details.vue';
import SolicitudClass from '@/entities/solicitud/solicitud-details.component';
import SolicitudService from '@/entities/solicitud/solicitud.service';
import router from '@/router';
import AlertService from '@/shared/alert/alert.service';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('Solicitud Management Detail Component', () => {
    let wrapper: Wrapper<SolicitudClass>;
    let comp: SolicitudClass;
    let solicitudServiceStub: SinonStubbedInstance<SolicitudService>;

    beforeEach(() => {
      solicitudServiceStub = sinon.createStubInstance<SolicitudService>(SolicitudService);

      wrapper = shallowMount<SolicitudClass>(SolicitudDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { solicitudService: () => solicitudServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundSolicitud = { id: 'ABC' };
        solicitudServiceStub.find.resolves(foundSolicitud);

        // WHEN
        comp.retrieveSolicitud('ABC');
        await comp.$nextTick();

        // THEN
        expect(comp.solicitud).toBe(foundSolicitud);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundSolicitud = { id: 'ABC' };
        solicitudServiceStub.find.resolves(foundSolicitud);

        // WHEN
        comp.beforeRouteEnter({ params: { solicitudId: 'ABC' } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.solicitud).toBe(foundSolicitud);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        comp.previousState();
        await comp.$nextTick();

        expect(comp.$router.currentRoute.fullPath).toContain('/');
      });
    });
  });
});
