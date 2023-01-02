import { Component, Prop, Vue } from 'vue-property-decorator';

@Component
export default class TagsComponent extends Vue {
  @Prop({ required: true })
  public value!: string[];

  @Prop({ default: null })
  public label: string;

  @Prop({ default: 'primary' })
  public variant: string;

  get tags(): string[] {
    return this.value;
  }
  set tags(newValue: string[]) {
    this.$emit('input', newValue);
  }
}
