import { element, by, ElementFinder } from 'protractor';

export class PartnerComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-partner div table .btn-danger'));
    title = element.all(by.css('jhi-partner div h2#page-heading span')).first();

    async clickOnCreateButton() {
        await this.createButton.click();
    }

    async clickOnLastDeleteButton() {
        await this.deleteButtons.last().click();
    }

    async countDeleteButtons() {
        return this.deleteButtons.count();
    }

    async getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class PartnerUpdatePage {
    pageTitle = element(by.id('jhi-partner-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    partnerNameInput = element(by.id('field_partnerName'));
    partnerTypeSelect = element(by.id('field_partnerType'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    async setPartnerNameInput(partnerName) {
        await this.partnerNameInput.sendKeys(partnerName);
    }

    async getPartnerNameInput() {
        return this.partnerNameInput.getAttribute('value');
    }

    async setPartnerTypeSelect(partnerType) {
        await this.partnerTypeSelect.sendKeys(partnerType);
    }

    async getPartnerTypeSelect() {
        return this.partnerTypeSelect.element(by.css('option:checked')).getText();
    }

    async partnerTypeSelectLastOption() {
        await this.partnerTypeSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async save() {
        await this.saveButton.click();
    }

    async cancel() {
        await this.cancelButton.click();
    }

    getSaveButton(): ElementFinder {
        return this.saveButton;
    }
}

export class PartnerDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-partner-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-partner'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
