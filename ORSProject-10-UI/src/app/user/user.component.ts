import { Component } from '@angular/core';
import { BaseCtl } from '../base.component';
import { ServiceLocatorService } from '../service-locator.service';
import { ActivatedRoute } from '@angular/router';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html'
})
export class UserComponent extends BaseCtl {

  fileToUpload: File | null = null;
  imagePreview: any = null;
datepicker: any;

  constructor(private locator: ServiceLocatorService, route: ActivatedRoute, private httpClient: HttpClient) {
    super(locator.endpoints.USER, locator, route);
  }

  // 📁 FILE SELECT + PREVIEW
  onFileSelect(files: FileList | null) {
    if (!files || files.length === 0) {
      console.log('No file selected');
      return;
    }

    this.fileToUpload = files.item(0);

    // Preview
    if (this.fileToUpload) {
      const reader = new FileReader();
      reader.onload = (e: any) => {
        this.imagePreview = e.target.result;
      };
      reader.readAsDataURL(this.fileToUpload);
    }

    console.log(this.fileToUpload);
  }
  onUpload() {
    this.submit((id: any) => {
      if (id) {
        this.myFile();
      }
    });
  }

  // 📤 IMAGE UPLOAD METHOD
  myFile() {
    if (!this.fileToUpload) {
      console.log('No file selected');
      return;
    }

    const formData = new FormData();
    formData.append('file', this.fileToUpload);

    this.httpClient.post('http://localhost:8080/User/profilePic/' + this.form.data.id, formData).subscribe((data: any) => {
      console.log('Upload Success:', data);
    },
      (error: any) => {
        console.log('Upload Error:', error);
      },
    );
  }
}