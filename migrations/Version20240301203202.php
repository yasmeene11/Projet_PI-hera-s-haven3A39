<?php

declare(strict_types=1);

namespace DoctrineMigrations;

use Doctrine\DBAL\Schema\Schema;
use Doctrine\Migrations\AbstractMigration;

/**
 * Auto-generated Migration: Please modify to your needs!
 */
final class Version20240301203202 extends AbstractMigration
{
    public function getDescription(): string
    {
        return '';
    }

    public function up(Schema $schema): void
    {
        // this up() migration is auto-generated, please modify it to your needs
        $this->addSql('ALTER TABLE cash_register ADD input INT NOT NULL, ADD output INT NOT NULL, ADD date_transaction DATE NOT NULL, ADD type VARCHAR(30) NOT NULL, ADD somme DOUBLE PRECISION NOT NULL, ADD id_entity INT NOT NULL, DROP balance, DROP expenses, DROP donation_total, DROP adoption_fee_total, DROP boarding_fee_total');
    }

    public function down(Schema $schema): void
    {
        // this down() migration is auto-generated, please modify it to your needs
        $this->addSql('ALTER TABLE cash_register ADD expenses DOUBLE PRECISION NOT NULL, ADD donation_total DOUBLE PRECISION NOT NULL, ADD adoption_fee_total DOUBLE PRECISION NOT NULL, ADD boarding_fee_total DOUBLE PRECISION NOT NULL, DROP input, DROP output, DROP date_transaction, DROP type, DROP id_entity, CHANGE somme balance DOUBLE PRECISION NOT NULL');
    }
}
