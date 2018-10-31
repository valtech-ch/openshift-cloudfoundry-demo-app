import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IPartnerAccount } from 'app/shared/model/partner-account.model';
import { PartnerAccountService } from './partner-account.service';
import { IPartner } from 'app/shared/model/partner.model';
import { PartnerService } from 'app/entities/partner';
import { IPartnerUser } from 'app/shared/model/partner-user.model';
import { PartnerUserService } from 'app/entities/partner-user';

@Component({
    selector: 'jhi-partner-account-update',
    templateUrl: './partner-account-update.component.html'
})
export class PartnerAccountUpdateComponent implements OnInit {
    partnerAccount: IPartnerAccount;
    isSaving: boolean;

    partners: IPartner[];

    partnerusers: IPartnerUser[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private partnerAccountService: PartnerAccountService,
        private partnerService: PartnerService,
        private partnerUserService: PartnerUserService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ partnerAccount }) => {
            this.partnerAccount = partnerAccount;
        });
        this.partnerService.query().subscribe(
            (res: HttpResponse<IPartner[]>) => {
                this.partners = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.partnerUserService.query().subscribe(
            (res: HttpResponse<IPartnerUser[]>) => {
                this.partnerusers = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.partnerAccount.id !== undefined) {
            this.subscribeToSaveResponse(this.partnerAccountService.update(this.partnerAccount));
        } else {
            this.subscribeToSaveResponse(this.partnerAccountService.create(this.partnerAccount));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IPartnerAccount>>) {
        result.subscribe((res: HttpResponse<IPartnerAccount>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackPartnerById(index: number, item: IPartner) {
        return item.id;
    }

    trackPartnerUserById(index: number, item: IPartnerUser) {
        return item.id;
    }
}
