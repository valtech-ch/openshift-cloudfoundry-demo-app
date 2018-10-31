/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { PinguinAppTestModule } from '../../../test.module';
import { PartnerRoleUpdateComponent } from 'app/entities/partner-role/partner-role-update.component';
import { PartnerRoleService } from 'app/entities/partner-role/partner-role.service';
import { PartnerRole } from 'app/shared/model/partner-role.model';

describe('Component Tests', () => {
    describe('PartnerRole Management Update Component', () => {
        let comp: PartnerRoleUpdateComponent;
        let fixture: ComponentFixture<PartnerRoleUpdateComponent>;
        let service: PartnerRoleService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PinguinAppTestModule],
                declarations: [PartnerRoleUpdateComponent]
            })
                .overrideTemplate(PartnerRoleUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(PartnerRoleUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PartnerRoleService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new PartnerRole('123');
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.partnerRole = entity;
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
                    const entity = new PartnerRole();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.partnerRole = entity;
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
