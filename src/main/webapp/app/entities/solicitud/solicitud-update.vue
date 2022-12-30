<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <div>
          <message v-show="showWelcomeMessage" :title="solucion.titulo" :description="solucion.descripcion" />
          <formio
            v-if="isFormioVisible"
            :submission="formContext.submission"
            ref="formio"
            :form="form"
            :options="options"
            v-on:submit="handleSubmit"
            v-on:formLoad="handleFormLoad"
          ></formio>
          <send-solicitud v-if="isSendVisible" @send="handleSend"></send-solicitud>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./solicitud-update.component.ts"></script>
