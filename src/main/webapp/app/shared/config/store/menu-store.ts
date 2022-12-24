import { Module } from 'vuex';

export const menuStore: Module<any, any> = {
  state: {
    menu: localStorage.getItem('menu') || [],
  },
  getters: {
    menu: state => state.menu,
  },
  mutations: {
    menu(state, newMenu) {
      state.menu = newMenu;
      localStorage.setItem('menu', newMenu);
    },
    addItem(state, item) {
      state.menu.push(item);
      localStorage.setItem('menu', state.menu);
    },
    removeItem(state, item) {
      const componentes = state.menu.filter((value, index, arr) => {
        if (value.id === item.id) {
          arr.splice(index, 1);
          return true;
        }
        return false;
      });
      localStorage.setItem('menu', componentes);
    },
  },
};
