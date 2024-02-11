<?php

namespace App\Entity;

use App\Repository\AdoptionRepository;
use Doctrine\ORM\Mapping as ORM;
use Doctrine\DBAL\Types\Types;

#[ORM\Entity(repositoryClass: AdoptionRepository::class)]
class Adoption
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column(name: 'adoptionId')]
    private ?int $adoptionId = null;

    #[ORM\Column(type: Types::DATE_MUTABLE)]
    private ?\DateTimeInterface $Adoption_Date = null;

    #[ORM\Column(length: 255)]
    private ?string $Adoption_Status = null;

    #[ORM\Column]
    private ?float $Adoption_Fee = null;

    #[ORM\ManyToOne(inversedBy: 'adoptions')]
    #[ORM\JoinColumn(nullable: false, name: 'Account_Key', referencedColumnName: 'accountId')]
    private ?Account $Account_Key = null;

    #[ORM\ManyToOne(inversedBy: 'adoptions')]
    #[ORM\JoinColumn(nullable: false, name: 'Animal_Key', referencedColumnName: 'animalId')]
    private ?Animal $Animal_Key = null;

    public function getAdoptionId(): ?int
    {
        return $this->adoptionId;
    }

    public function getAdoptionDate(): ?\DateTimeInterface
    {
        return $this->Adoption_Date;
    }

    public function setAdoptionDate(\DateTimeInterface $Adoption_Date): static
    {
        $this->Adoption_Date = $Adoption_Date;

        return $this;
    }

    public function getAdoptionStatus(): ?string
    {
        return $this->Adoption_Status;
    }

    public function setAdoptionStatus(string $Adoption_Status): static
    {
        $this->Adoption_Status = $Adoption_Status;

        return $this;
    }

    public function getAdoptionFee(): ?float
    {
        return $this->Adoption_Fee;
    }

    public function setAdoptionFee(float $Adoption_Fee): static
    {
        $this->Adoption_Fee = $Adoption_Fee;

        return $this;
    }

    public function getAccountKey(): ?Account
    {
        return $this->Account_Key;
    }

    public function setAccountKey(?Account $Account_Key): static
    {
        $this->Account_Key = $Account_Key;

        return $this;
    }

    public function getAnimalKey(): ?Animal
    {
        return $this->Animal_Key;
    }

    public function setAnimalKey(?Animal $Animal_Key): static
    {
        $this->Animal_Key = $Animal_Key;

        return $this;
    }
}
