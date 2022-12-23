<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="solucion">
        <h2 class="jh-entity-heading" data-cy="solucionDetailsHeading">
          <span v-text="$t('archeApp.solucion.detail.title')">Solucion</span> {{ solucion.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('archeApp.solucion.titulo')">Titulo</span>
          </dt>
          <dd>
            <span>{{ solucion.titulo }}</span>
          </dd>
          <dt>
            <span v-text="$t('archeApp.solucion.descripcion')">Descripcion</span>
          </dt>
          <dd>
            <span>{{ solucion.descripcion }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link v-if="solucion.id" :to="{ name: 'SolucionEdit', params: { solucionId: solucion.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./solucion-details.component.ts"></script>
