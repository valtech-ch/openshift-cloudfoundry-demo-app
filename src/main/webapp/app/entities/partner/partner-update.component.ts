import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IPartner } from 'app/shared/model/partner.model';
import { PartnerService } from './partner.service';

@Component({
    selector: 'jhi-partner-update',
    templateUrl: './partner-update.component.html'
})
export class PartnerUpdateComponent implements OnInit {
    partner: IPartner;
    isSaving: boolean;

    constructor(private partnerService: PartnerService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ partner }) => {
            this.partner = partner;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.partner.id !== undefined) {
            this.subscribeToSaveResponse(this.partnerService.update(this.partner));
        } else {
            this.subscribeToSaveResponse(this.partnerService.create(this.partner));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IPartner>>) {
        result.subscribe((res: HttpResponse<IPartner>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
}
