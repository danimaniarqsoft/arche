import { Component, Prop, Vue } from 'vue-property-decorator';

@Component
export default class Message extends Vue {
  @Prop({ default: null })
  public title: string;

  @Prop({ default: null })
  public description: string;

  @Prop({ default: null })
  public footer: string;

  @Prop({ default: 'primary' })
  public variant: string;

  get icon() {
    if (this.variant === 'success') {
      return 'check-circle-fill';
    } else if (this.variant === 'warning' || this.variant === 'danger') {
      return 'exclamation-triangle-fill';
    } else {
      return 'info-circle-fill';
    }
  }
}
