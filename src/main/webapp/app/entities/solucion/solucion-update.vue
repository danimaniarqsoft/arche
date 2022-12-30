<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="isPreview">
        <formio ref="formio" :form="form" :options="options" v-on:submit="handleSubmit"></formio>
      </div>
      <form v-else name="editForm" role="form" novalidate v-on:submit.prevent="handleSave()">
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
        <hr />
        <div class="text-right">
          <button type="button" id="cancel-save" data-cy="entityCreateCancelButton" class="btn btn-secondary" v-on:click="previousState()">
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
          </button>
          <b-button variant="primary" id="save-entity" :disabled="$v.solucion.$invalid || isSaving" @click="handleSave()">
            <b-icon v-if="isSaving" icon="save" animation="fade" aria-hidden="true"></b-icon>
            <b-icon v-else icon="save" aria-hidden="true"></b-icon>&nbsp;{{ $t('entity.action.save') }}
          </b-button>
          <b-dropdown variant="outline-primary" dropright no-caret>
            <template #button-content>
              <b-icon v-if="isPublishing" icon="megaphone" animation="fade" aria-hidden="true"></b-icon>
              <b-icon v-else icon="megaphone" aria-hidden="true"></b-icon>&nbsp;Publicar solución
            </template>
            <b-dropdown-form>
              <div class="text-center">
                <small class="text-center">¿Esta seguro de publicar?</small>
              </div>
              <b-dropdown-divider></b-dropdown-divider>
              <b-dropdown-item-button @click="handlePublicar()" class="text-center">Aceptar</b-dropdown-item-button>
            </b-dropdown-form>
          </b-dropdown>
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
