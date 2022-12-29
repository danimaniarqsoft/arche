<template>
  <div>
    <b-sidebar
      id="sidebar-right"
      :visible="isVisible"
      title="Secciones"
      shadow
      no-close-on-backdrop
      no-close-on-esc
      no-close-on-route-change
      no-enforce-focus
      no-header-close
    >
      <template #default="">
        <div class="p-3">
          <div class="mb-2 text-center">
            <b-avatar size="6rem" variant="success" rounded></b-avatar>
          </div>
          <hr />

          <b-nav vertical class="w-100">
            <b-nav-item
              v-for="item in menu"
              v-bind:key="item._id"
              @click="loadForm(item)"
              exact
              exact-active-class="active"
              class="d-inline-block"
            >
              <b-icon :icon="item.icon" variant="success"></b-icon>
              {{ item.titulo }}
            </b-nav-item>
          </b-nav>
        </div>
      </template>
    </b-sidebar>
  </div>
</template>

<script lang="ts" src="./side-navbar.component.ts"></script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.badge-success {
  color: #ffff;
}
</style>
