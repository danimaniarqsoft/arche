import { Component, Prop, Vue } from 'vue-property-decorator';

@Component
export default class DateTimePickerComponent extends Vue {
  @Prop({ required: true })
  public value!: Date;

  @Prop({ default: null })
  public label: string;

  @Prop({ default: 'America/Mexico_City' })
  public timezone: string;

  @Prop({ default: 'es-MX' })
  public locale: string;

  get dateTime() {
    return this.value;
  }

  set dateTime(newDate: Date) {
    this.$emit('input', newDate);
  }

  public initTimeConfig = {
    dateTime: {
      timeAdjust: '23:59:59',
    },
  };
}
