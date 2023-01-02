<template>
  <div>
    <label class="form-control-label">{{ label }}</label>
    <v-date-picker mode="dateTime" v-model="dateTime" :model-config="initTimeConfig" :timezone="timezone" :locale="locale">
      <template v-slot="{ inputValue, inputEvents }">
        <b-form-input :value="inputValue" v-on="inputEvents" />
      </template>
    </v-date-picker>
  </div>
</template>

<script lang="ts" src="./date-time-picker.component.ts"></script>
