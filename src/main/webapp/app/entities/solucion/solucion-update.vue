<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="archeApp.solucion.home.createOrEditLabel"
          data-cy="SolucionCreateUpdateHeading"
          v-text="$t('archeApp.solucion.home.createOrEditLabel')"
        >
          Create or edit a Solucion
        </h2>
        <div>
          <div class="form-group" v-if="solucion.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="solucion.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('archeApp.solucion.titulo')" for="solucion-titulo">Titulo</label>
            <input
              type="text"
              class="form-control"
              name="titulo"
              id="solucion-titulo"
              data-cy="titulo"
              :class="{ valid: !$v.solucion.titulo.$invalid, invalid: $v.solucion.titulo.$invalid }"
              v-model="$v.solucion.titulo.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('archeApp.solucion.descripcion')" for="solucion-descripcion">Descripcion</label>
            <input
              type="text"
              class="form-control"
              name="descripcion"
              id="solucion-descripcion"
              data-cy="descripcion"
              :class="{ valid: !$v.solucion.descripcion.$invalid, invalid: $v.solucion.descripcion.$invalid }"
              v-model="$v.solucion.descripcion.$model"
            />
          </div>
        </div>
        <b-card no-body>
          <b-tabs nav-wrapper-class="w-35" pills card vertical end>
            <b-tab v-for="form in forms" v-bind:key="form._id">
              <template #title>
                <b-icon v-if="isInComponents(form)" icon="check2-circle" variant="success"></b-icon>
                <b-icon class="component-item" v-else icon="circle" variant="success"></b-icon>
                &nbsp;&nbsp;
                {{ form.title }}
              </template>
              <div v-if="isInComponents(form)">
                <div class="text-right">
                  <b-button :id="form._id" variant="danger" @click="handleRemoveComponente(form)"
                    ><b-icon icon="x-circle" variant="danger"></b-icon>&nbsp; <b><i>Remover</i></b>
                  </b-button>
                </div>
              </div>
              <div v-else class="text-right">
                <icon-picker @seleted="handleAddComponente($event, form)"></icon-picker>
              </div>
              <formio :form="form" :options="options"></formio>
            </b-tab>
          </b-tabs>
        </b-card>
        <div>
          <button type="button" id="cancel-save" data-cy="entityCreateCancelButton" class="btn btn-secondary" v-on:click="previousState()">
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
          </button>
          <button
            type="submit"
            id="save-entity"
            data-cy="entityCreateSaveButton"
            :disabled="$v.solucion.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./solucion-update.component.ts"></script>
<style scoped>
.active .b-icon {
  color: white !important;
}
</style>
