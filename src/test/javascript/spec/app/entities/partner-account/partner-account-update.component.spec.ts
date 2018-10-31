/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { PinguinAppTestModule } from '../../../test.module';
import { PartnerAccountUpdateComponent } from 'app/entities/partner-account/partner-account-update.component';
import { PartnerAccountService } from 'app/entities/partner-account/partner-account.service';
import { PartnerAccount } from 'app/shared/model/partner-account.model';

describe('Component Tests', () => {
    describe('PartnerAccount Management Update Component', () => {
        let comp: PartnerAccountUpdateComponent;
        let fixture: ComponentFixture<PartnerAccountUpdateComponent>;
        let service: PartnerAccountService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PinguinAppTestModule],
                declarations: [PartnerAccountUpdateComponent]
            })
                .overrideTemplate(PartnerAccountUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(PartnerAccountUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PartnerAccountService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new PartnerAccount('123');
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.partnerAccount = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new PartnerAccount();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.partnerAccount = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
