import axios from 'axios';

const baseApiUrl = 'http://localhost:3001/form';

export default class FormService {
  public retrieveFormById(formId: string): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .get(`${baseApiUrl}/${formId}`)
        .then(res => {
          resolve(res);
        })
        .catch(err => {
          reject(err);
        });
    });
  }
  public retrieveAllForms(): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .get('http://localhost:3001/form?type=form')
        .then(res => {
          resolve(res);
        })
        .catch(err => {
          reject(err);
        });
    });
  }
}
