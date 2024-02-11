<?php

declare(strict_types=1);

namespace DoctrineMigrations;

use Doctrine\DBAL\Schema\Schema;
use Doctrine\Migrations\AbstractMigration;

/**
 * Auto-generated Migration: Please modify to your needs!
 */
final class Version20240211171741 extends AbstractMigration
{
    public function getDescription(): string
    {
        return '';
    }

    public function up(Schema $schema): void
    {
        // this up() migration is auto-generated, please modify it to your needs
        $this->addSql('ALTER TABLE adoption ADD Animal_Key INT NOT NULL');
        $this->addSql('ALTER TABLE adoption ADD CONSTRAINT FK_EDDEB6A9620EA577 FOREIGN KEY (Animal_Key) REFERENCES animal (animalId)');
        $this->addSql('CREATE INDEX IDX_EDDEB6A9620EA577 ON adoption (Animal_Key)');
    }

    public function down(Schema $schema): void
    {
        // this down() migration is auto-generated, please modify it to your needs
        $this->addSql('ALTER TABLE adoption DROP FOREIGN KEY FK_EDDEB6A9620EA577');
        $this->addSql('DROP INDEX IDX_EDDEB6A9620EA577 ON adoption');
        $this->addSql('ALTER TABLE adoption DROP Animal_Key');
    }
}
