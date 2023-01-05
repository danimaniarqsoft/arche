import { Component, Prop, Vue } from 'vue-property-decorator';

@Component
export default class ErrorComponent extends Vue {
  @Prop({ required: true })
  public value: [];

  get errors() {
    return this.value;
  }

  set errors(newValue: any[]) {
    this.$emit('input', newValue);
  }
}
