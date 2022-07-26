import { Injectable } from '@angular/core';
import { AngularFireStorage } from '@angular/fire/compat/storage';
import { } from '@angular/fire/firestore';

@Injectable({
  providedIn: 'root'
})
export class FirebaseService {

  constructor(private storage: AngularFireStorage) { }

  uploadFile(file: File) {
    this.storage.upload(file.name, file)
  }
}
