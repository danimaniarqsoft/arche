<template>
  <div>
    <h2 id="page-heading" data-cy="SolucionHeading">
      <span v-text="$t('archeApp.solucion.home.title')" id="solucion-heading">Solucions</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('archeApp.solucion.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'SolucionCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-solucion"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('archeApp.solucion.home.createLabel')"> Create a new Solucion </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && solucions && solucions.length === 0">
      <span v-text="$t('archeApp.solucion.home.notFound')">No solucions found</span>
    </div>
    <div class="table-responsive" v-if="solucions && solucions.length > 0">
      <table class="table table-striped" aria-describedby="solucions">
        <thead>
          <tr>
            <th scope="row" v-on:click="changeOrder('id')">
              <span v-text="$t('global.field.id')">ID</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('titulo')">
              <span v-text="$t('archeApp.solucion.titulo')">Titulo</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'titulo'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('estado')">
              <span v-text="$t('archeApp.solucion.estado')">Estado</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'estado'"></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="solucion in solucions" :key="solucion.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'SolucionView', params: { solucionId: solucion.id } }">{{ solucion.id }}</router-link>
            </td>
            <td>{{ solucion.titulo }}</td>
            <td v-text="$t('archeApp.EstadoSolucion.' + solucion.estado)">{{ solucion.estado }}</td>
            <td class="text-right">
              <div>
                <router-link :to="{ name: 'SolucionEdit', params: { solucionId: solucion.id } }" custom v-slot="{ navigate }">
                  <b-button @click="navigate" variant="primary">
                    <b-icon icon="pencil-square"></b-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </b-button>
                </router-link>
                <router-link :to="{ name: 'SolicitudEdit', params: { solicitudId: solucion.id } }" custom v-slot="{ navigate }">
                  <b-button @click="navigate" variant="primary">
                    <b-icon icon="arrow-right-square"></b-icon>
                    <span class="d-none d-md-inline">Crear solicitud</span>
                  </b-button>
                </router-link>
              </div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    <b-modal ref="removeEntity" id="removeEntity">
      <span slot="modal-title"
        ><span id="archeApp.solucion.delete.question" data-cy="solucionDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-solucion-heading" v-text="$t('archeApp.solucion.delete.question', { id: removeId })">
          Are you sure you want to delete this Solucion?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-solucion"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeSolucion()"
        >
          Delete
        </button>
      </div>
    </b-modal>
    <div v-show="solucions && solucions.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./solucion.component.ts"></script>
