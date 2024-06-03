import { Component, Input } from '@angular/core';
import { LoginService } from 'src/app/services/login.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent {
  @Input() isLogged!: boolean;
  @Input() isAdmin!: boolean;
  @Input() username!: string;

  constructor(private loginService: LoginService) { }
  active!: number;

  public login(): void {
    this.loginService.login();
  }
  public logout(): void {
    this.loginService.logout();
  }
}