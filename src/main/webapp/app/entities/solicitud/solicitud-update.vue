<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <div>
          <div v-show="isFormEmpty">
            <b-alert show variant="success">
              <h4 class="alert-heading">Bienvenido al registro de solucitudes</h4>
              <p>
                Selecciona algún menú de la izquiera para comenzar a llenar tu solicitud. La fecha máxima para recibir solicitudes es el ...
              </p>
              <hr />
              <p class="mb-0">Si tienes dudas, puedes consultarnos en nuestro centro de soporte técnico.</p>
            </b-alert>
          </div>
          <formio
            :submission="context.submission"
            ref="formio"
            :form="form"
            :options="options"
            v-on:submit="handleSubmit"
            v-on:formLoad="handleFormLoad"
          ></formio>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./solicitud-update.component.ts"></script>
