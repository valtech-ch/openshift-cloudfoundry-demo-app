import { element, by, ElementFinder } from 'protractor';

export class PartnerRoleComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-partner-role div table .btn-danger'));
    title = element.all(by.css('jhi-partner-role div h2#page-heading span')).first();

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

export class PartnerRoleUpdatePage {
    pageTitle = element(by.id('jhi-partner-role-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    roleNameInput = element(by.id('field_roleName'));
    partnerAccountSelect = element(by.id('field_partnerAccount'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    async setRoleNameInput(roleName) {
        await this.roleNameInput.sendKeys(roleName);
    }

    async getRoleNameInput() {
        return this.roleNameInput.getAttribute('value');
    }

    async partnerAccountSelectLastOption() {
        await this.partnerAccountSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async partnerAccountSelectOption(option) {
        await this.partnerAccountSelect.sendKeys(option);
    }

    getPartnerAccountSelect(): ElementFinder {
        return this.partnerAccountSelect;
    }

    async getPartnerAccountSelectedOption() {
        return this.partnerAccountSelect.element(by.css('option:checked')).getText();
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

export class PartnerRoleDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-partnerRole-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-partnerRole'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
