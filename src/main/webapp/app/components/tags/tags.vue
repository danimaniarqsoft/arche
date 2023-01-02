<template>
  <div>
    <label for="tags-pills">{{ label }}</label>
    <b-form-tags
      input-id="tags-pills"
      v-model="tags"
      :tag-variant="variant"
      tag-pills
      size="lg"
      :limit="5"
      placeholder="Ingrese un tag"
    ></b-form-tags>
  </div>
</template>

<script lang="ts" src="./tags.component.ts"></script>
