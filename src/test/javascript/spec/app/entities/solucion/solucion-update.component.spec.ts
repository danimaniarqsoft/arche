/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import SolucionUpdateComponent from '@/entities/solucion/solucion-update.vue';
import SolucionClass from '@/entities/solucion/solucion-update.component';
import SolucionService from '@/entities/solucion/solucion.service';

import AlertService from '@/shared/alert/alert.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.use(ToastPlugin);
localVue.component('font-awesome-icon', {});
localVue.component('b-input-group', {});
localVue.component('b-input-group-prepend', {});
localVue.component('b-form-datepicker', {});
localVue.component('b-form-input', {});

describe('Component Tests', () => {
  describe('Solucion Management Update Component', () => {
    let wrapper: Wrapper<SolucionClass>;
    let comp: SolucionClass;
    let solucionServiceStub: SinonStubbedInstance<SolucionService>;

    beforeEach(() => {
      solucionServiceStub = sinon.createStubInstance<SolucionService>(SolucionService);

      wrapper = shallowMount<SolucionClass>(SolucionUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          solucionService: () => solucionServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 'ABC' };
        comp.solucion = entity;
        solucionServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(solucionServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.solucion = entity;
        solucionServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(solucionServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundSolucion = { id: 'ABC' };
        solucionServiceStub.find.resolves(foundSolucion);
        solucionServiceStub.retrieve.resolves([foundSolucion]);

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
