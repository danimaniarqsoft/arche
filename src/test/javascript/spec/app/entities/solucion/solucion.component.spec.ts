/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import SolucionComponent from '@/entities/solucion/solucion.vue';
import SolucionClass from '@/entities/solucion/solucion.component';
import SolucionService from '@/entities/solucion/solucion.service';
import AlertService from '@/shared/alert/alert.service';

const localVue = createLocalVue();
localVue.use(ToastPlugin);

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('b-badge', {});
localVue.component('jhi-sort-indicator', {});
localVue.directive('b-modal', {});
localVue.component('b-button', {});
localVue.component('router-link', {});

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  describe('Solucion Management Component', () => {
    let wrapper: Wrapper<SolucionClass>;
    let comp: SolucionClass;
    let solucionServiceStub: SinonStubbedInstance<SolucionService>;

    beforeEach(() => {
      solucionServiceStub = sinon.createStubInstance<SolucionService>(SolucionService);
      solucionServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<SolucionClass>(SolucionComponent, {
        store,
        i18n,
        localVue,
        stubs: { jhiItemCount: true, bPagination: true, bModal: bModalStub as any },
        provide: {
          solucionService: () => solucionServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      solucionServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 'ABC' }] });

      // WHEN
      comp.retrieveAllSolucions();
      await comp.$nextTick();

      // THEN
      expect(solucionServiceStub.retrieve.called).toBeTruthy();
      expect(comp.solucions[0]).toEqual(expect.objectContaining({ id: 'ABC' }));
    });

    it('should load a page', async () => {
      // GIVEN
      solucionServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 'ABC' }] });
      comp.previousPage = 1;

      // WHEN
      comp.loadPage(2);
      await comp.$nextTick();

      // THEN
      expect(solucionServiceStub.retrieve.called).toBeTruthy();
      expect(comp.solucions[0]).toEqual(expect.objectContaining({ id: 'ABC' }));
    });

    it('should not load a page if the page is the same as the previous page', () => {
      // GIVEN
      solucionServiceStub.retrieve.reset();
      comp.previousPage = 1;

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(solucionServiceStub.retrieve.called).toBeFalsy();
    });

    it('should re-initialize the page', async () => {
      // GIVEN
      solucionServiceStub.retrieve.reset();
      solucionServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 'ABC' }] });

      // WHEN
      comp.loadPage(2);
      await comp.$nextTick();
      comp.clear();
      await comp.$nextTick();

      // THEN
      expect(solucionServiceStub.retrieve.callCount).toEqual(3);
      expect(comp.page).toEqual(1);
      expect(comp.solucions[0]).toEqual(expect.objectContaining({ id: 'ABC' }));
    });

    it('should calculate the sort attribute for an id', () => {
      // WHEN
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['id,asc']);
    });

    it('should calculate the sort attribute for a non-id attribute', () => {
      // GIVEN
      comp.propOrder = 'name';

      // WHEN
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['name,asc', 'id']);
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      solucionServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 'ABC' });
      expect(solucionServiceStub.retrieve.callCount).toEqual(1);

      comp.removeSolucion();
      await comp.$nextTick();

      // THEN
      expect(solucionServiceStub.delete.called).toBeTruthy();
      expect(solucionServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
