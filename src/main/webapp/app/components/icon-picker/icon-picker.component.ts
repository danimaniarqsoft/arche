import { Component, Vue } from 'vue-property-decorator';

@Component
export default class IconPickerComponent extends Vue {
  public icons = [
    'file-earmark-text',
    'person-fill',
    'person-badge',
    'people-fill',
    'archive',
    'bar-chart',
    'files',
    'upload',
    'cloud-upload-fill',
    'cloud-upload',
    'blockquote-left',
    'book',
    'bookmark',
    'bookmarks',
    'building',
    'clipboard-data',
  ];
  public selectedIcon = '';

  public handleSelectedIcon(selected: string) {
    this.$emit('seleted', selected);
    this.selectedIcon = selected;
  }
}
