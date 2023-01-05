import { Form, FormBuilder } from 'vue-formiojs';
import 'formiojs/dist/formio.full.min.css';

import IconPickerComponent from '@/components/icon-picker/icon-picker.vue';
import MessageComponent from '@/components/message/message.vue';
import ErrorComponent from '@/components/error/error.vue';
import ConfirmationComponent from '@/components/confirmation/confirmation.vue';
import DateTimePickerComponent from '@/components/date-time-picker/date-time-picker.vue';
import TagsComponent from '@/components/tags/tags.vue';
import { VueEditor } from 'vue2-editor';
import VCalendar from 'v-calendar';

export function initCustomVue(vue) {
  vue.use(VCalendar);
  vue.component('formio', Form);
  vue.component('formbuilder', FormBuilder);
  vue.component('icon-picker', IconPickerComponent);
  vue.component('message', MessageComponent);
  vue.component('confirmation', ConfirmationComponent);
  vue.component('vue-editor', VueEditor);
  vue.component('tags', TagsComponent);
  vue.component('date-time-picker', DateTimePickerComponent);
  vue.component('errors', ErrorComponent);
}
