import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IPartnerUser } from 'app/shared/model/partner-user.model';
import { PartnerUserService } from './partner-user.service';

@Component({
    selector: 'jhi-partner-user-update',
    templateUrl: './partner-user-update.component.html'
})
export class PartnerUserUpdateComponent implements OnInit {
    partnerUser: IPartnerUser;
    isSaving: boolean;

    constructor(private partnerUserService: PartnerUserService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ partnerUser }) => {
            this.partnerUser = partnerUser;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.partnerUser.id !== undefined) {
            this.subscribeToSaveResponse(this.partnerUserService.update(this.partnerUser));
        } else {
            this.subscribeToSaveResponse(this.partnerUserService.create(this.partnerUser));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IPartnerUser>>) {
        result.subscribe((res: HttpResponse<IPartnerUser>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
}
