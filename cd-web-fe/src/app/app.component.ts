import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { AuthService } from './services/auth.service';
import { LoaderService } from './services/loader.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {
  title = 'cd-web-fe';
  isLoading = false;
  constructor(private loaderService: LoaderService, private activatedRoute: ActivatedRoute, private authService: AuthService) {
  }

  ngOnInit() {
    this.initLoading();
    const token = '' + window.sessionStorage.getItem('basicToken')
    console.log(token)
    if (token != '') {
      this.authService.token.next(token);
      console.log(this.authService.decodeToken(token))
    }
  }

  initLoading() {
    this.loaderService.isLoading.subscribe(_x => {
      this.isLoading = _x
    })
  }
}
