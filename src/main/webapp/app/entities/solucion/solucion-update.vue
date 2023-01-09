<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="isPreview">
        <div v-show="showWelcomeMessage" v-html="solucion.mensaje.bienvenida"></div>
        <formio
          v-if="isFormioVisible"
          :form="form"
          :options="formOptions"
          :language="formOptions.language"
          v-on:submit="handleSubmit"
        ></formio>
        <send-solicitud
          v-if="isSendVisible"
          :isSent="isSent"
          :status="isSaving"
          @send="handleSend"
          :terminos="solucion.mensaje.terminos"
        ></send-solicitud>
      </div>
      <div v-else>
        <message
          v-if="solucion.estado === 'PUBLICADA'"
          title="Solución publicada"
          description="Su solución se encuentra publicada correctamente. Ya no se podrán relizar cambios "
        />

        <h2
          class="mb-5"
          id="archeApp.solucion.home.createOrEditLabel"
          data-cy="SolucionCreateUpdateHeading"
          v-text="$t('archeApp.solucion.home.createOrEditLabel')"
        >
          Create or edit a Solucion
        </h2>
        <b-tabs v-model="tabIndex">
          <b-tab class="mt-4" :title-link-class="linkClass(0)">
            <template #title> <b-icon icon="info-circle"></b-icon> Información de la solución </template>
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
                <label class="form-control-label" v-text="$t('archeApp.solucion.descripcion')" for="solucion-descripcion"
                  >Descripcion</label
                >
                <b-form-textarea
                  id="solucion-descripcion"
                  v-model="$v.solucion.descripcion.$model"
                  :class="{ valid: !$v.solucion.descripcion.$invalid, invalid: $v.solucion.descripcion.$invalid }"
                  rows="2"
                  max-rows="2"
                ></b-form-textarea>
              </div>
              <div class="form-group">
                <tags v-model="$v.solucion.tags.$model" variant="primary" label="Tags" />
              </div>
            </div>
          </b-tab>
          <b-tab class="mt-4" :title-link-class="linkClass(1)">
            <template #title> <b-icon icon="chat-left-text"></b-icon> Mensajes </template>
            <div class="form-group">
              <label class="form-control-label">Mensaje de bienvenida</label>
              <vue-editor id="mensaje-bienvenida" v-model="$v.solucion.mensaje.bienvenida.$model"></vue-editor>
            </div>
            <div class="form-group">
              <label class="form-control-label">Términos y condiciones</label>
              <vue-editor id="mensaje-terminos" v-model="$v.solucion.mensaje.terminos.$model"></vue-editor>
            </div>
          </b-tab>
          <b-tab class="mt-4" :title-link-class="linkClass(2)">
            <template #title> <b-icon icon="calendar3-week"></b-icon> Calendario </template>
            <div class="form-group">
              <date-time-picker label="Fecha de apertura" v-model="solucion.calendario.fechaInicio" />
            </div>
            <div class="form-group">
              <date-time-picker label="Fecha limite para recibir solicitudes" v-model="solucion.calendario.fechaFinSolicitud" />
            </div>
            <div class="form-group">
              <date-time-picker label="Fecha limite para evaluar" v-model="solucion.calendario.fechaFinRevision" />
            </div>
          </b-tab>
          <b-tab class="mt-4" :title-link-class="linkClass(3)">
            <template #title> <b-icon icon="puzzle"></b-icon> Componentes </template>
            <b-card no-body>
              <b-tabs nav-wrapper-class="w-35" pills card vertical end>
                <b-tab v-for="form in forms" v-bind:key="form._id">
                  <template #title>
                    <b-icon v-if="isFormSelected(form)" icon="check2-circle" variant="success"></b-icon>
                    <b-icon class="component-item" v-else icon="circle" variant="success"></b-icon>
                    &nbsp;&nbsp;
                    {{ form.title }}
                  </template>
                  <div v-if="isFormSelected(form)">
                    <div class="text-right">
                      <b-button :id="form._id" variant="danger" @click="handleRemoveComponente(form)"
                        ><b-icon icon="x-circle" variant="danger"></b-icon>&nbsp; <b><i>Remover</i></b>
                      </b-button>
                    </div>
                  </div>
                  <div v-else class="text-right">
                    <icon-picker @seleted="handleAddComponente($event, form)"></icon-picker>
                  </div>
                  <formio :form="form" :options="formOptions" :language="formOptions.language"></formio>
                </b-tab>
              </b-tabs>
            </b-card>
          </b-tab>
          <b-tab class="mt-4" :title-link-class="linkClass(4)" disabled>
            <template #title> <b-icon icon="file-ruled"></b-icon> Reglas </template>
            <b-card no-body> </b-card>
          </b-tab>
          <b-tab class="mt-4" :title-link-class="linkClass(5)">
            <template #title> <b-icon icon="card-checklist"></b-icon> Cuestionario de Revisión </template>
            <formbuilder :form="cuestionario" :options="builderOptions"></formbuilder>
          </b-tab>
        </b-tabs>
        <div class="text-right card-body">
          <b-button variant="primary" id="save-entity" :disabled="$v.solucion.$invalid || isSaving" @click="handleSave()">
            <b-icon v-if="isSaving" icon="save" animation="fade" aria-hidden="true"></b-icon>
            <b-icon v-else icon="save" aria-hidden="true"></b-icon>&nbsp;{{ $t('entity.action.save') }}
          </b-button>

          <b-button
            variant="outline-danger"
            id="publish-solucion"
            :disabled="$v.solucion.$invalid || isPublishing"
            v-b-modal.confirmar-publicar-modal
          >
            <b-icon v-if="isPublishing" icon="megaphone" animation="fade" aria-hidden="true"></b-icon>
            <b-icon v-else icon="megaphone" aria-hidden="true"></b-icon>&nbsp;Publicar solución
          </b-button>

          <confirmation id="confirmar-publicar-modal" @confirmed="handlePublicarConfirmation"></confirmation>
        </div>
      </div>
    </div>
  </div>
</template>
<script lang="ts" src="./solucion-update.component.ts"></script>
<style scoped>
.card-header {
  background-color: #78c2ad !important;
  background-color: rgba(234, 35, 35, 0.03) !important;
}
.component-selected {
  color: white !important;
}
</style>
