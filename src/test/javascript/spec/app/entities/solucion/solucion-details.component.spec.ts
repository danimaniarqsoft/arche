/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import SolucionDetailComponent from '@/entities/solucion/solucion-details.vue';
import SolucionClass from '@/entities/solucion/solucion-details.component';
import SolucionService from '@/entities/solucion/solucion.service';
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
  describe('Solucion Management Detail Component', () => {
    let wrapper: Wrapper<SolucionClass>;
    let comp: SolucionClass;
    let solucionServiceStub: SinonStubbedInstance<SolucionService>;

    beforeEach(() => {
      solucionServiceStub = sinon.createStubInstance<SolucionService>(SolucionService);

      wrapper = shallowMount<SolucionClass>(SolucionDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { solucionService: () => solucionServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundSolucion = { id: 'ABC' };
        solucionServiceStub.find.resolves(foundSolucion);

        // WHEN
        comp.retrieveSolucion('ABC');
        await comp.$nextTick();

        // THEN
        expect(comp.solucion).toBe(foundSolucion);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundSolucion = { id: 'ABC' };
        solucionServiceStub.find.resolves(foundSolucion);

        // WHEN
        comp.beforeRouteEnter({ params: { solucionId: 'ABC' } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.solucion).toBe(foundSolucion);
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
