export default function buildQueryOpts(element) {
  if (element) {
    let filters = '';
    for (const [key, value] of Object.entries(element)) {
      if (value) {
        if (Array.isArray(value)) {
          if (value.length > 0) {
            let array = '';
            for (let index = 0; index < value.length; index++) {
              if (index > 0) {
                array += ',';
              }
              array += value[index];
            }
            filters += `&${key}=${array}`;
          }
        } else {
          filters += `&${key}=${value}`;
        }
      }
    }
    return filters;
  }
  return '';
}
