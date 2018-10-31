import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IPartnerRole } from 'app/shared/model/partner-role.model';
import { PartnerRoleService } from './partner-role.service';
import { IPartnerAccount } from 'app/shared/model/partner-account.model';
import { PartnerAccountService } from 'app/entities/partner-account';

@Component({
    selector: 'jhi-partner-role-update',
    templateUrl: './partner-role-update.component.html'
})
export class PartnerRoleUpdateComponent implements OnInit {
    partnerRole: IPartnerRole;
    isSaving: boolean;

    partneraccounts: IPartnerAccount[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private partnerRoleService: PartnerRoleService,
        private partnerAccountService: PartnerAccountService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ partnerRole }) => {
            this.partnerRole = partnerRole;
        });
        this.partnerAccountService.query().subscribe(
            (res: HttpResponse<IPartnerAccount[]>) => {
                this.partneraccounts = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.partnerRole.id !== undefined) {
            this.subscribeToSaveResponse(this.partnerRoleService.update(this.partnerRole));
        } else {
            this.subscribeToSaveResponse(this.partnerRoleService.create(this.partnerRole));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IPartnerRole>>) {
        result.subscribe((res: HttpResponse<IPartnerRole>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackPartnerAccountById(index: number, item: IPartnerAccount) {
        return item.id;
    }
}
