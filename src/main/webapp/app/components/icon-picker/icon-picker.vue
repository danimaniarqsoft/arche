<template>
  <div>
    <b-dropdown variant="primary" dropleft no-caret>
      <template #button-content> <b-icon icon="plus" aria-hidden="true"></b-icon>Agregar</template>
      <b-dropdown-form>
        <small><i>Selecciona ícono</i></small>
        <b-dropdown-divider></b-dropdown-divider>
        <b-button pill variant="light" size="sm" v-for="(icon, index) in icons" v-bind:key="index" @click="handleSelectedIcon(icon)">
          <b-icon :icon="icon" aria-hidden="true"></b-icon>
        </b-button>
      </b-dropdown-form>
    </b-dropdown>
  </div>
</template>

<script lang="ts" src="./icon-picker.component.ts"></script>

<style scoped></style>
