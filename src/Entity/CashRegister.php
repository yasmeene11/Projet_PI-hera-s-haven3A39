<?php

namespace App\Entity;

use App\Repository\CashRegisterRepository;
use Doctrine\DBAL\Types\Types;
use Doctrine\ORM\Mapping as ORM;

#[ORM\Entity(repositoryClass: CashRegisterRepository::class)]
class CashRegister
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column(name:'cashRegisterId')]
    private ?int $cashRegisterId = null;

    #[ORM\Column]
    private ?int $input = null;

    #[ORM\Column]
    private ?int $output = null;

    #[ORM\Column(type: Types::DATE_MUTABLE)]
    private ?\DateTimeInterface $dateTransaction = null;

    #[ORM\Column(length: 30)]
    private ?string $type = null;

    #[ORM\Column]
    private ?float $somme = null;

    #[ORM\Column]
    private ?int $idEntity = null;

    public function getcashRegisterId(): ?int
    {
        return $this->cashRegisterId;
    }

    public function getInput(): ?int
    {
        return $this->input;
    }

    public function setInput(int $input): static
    {
        $this->input = $input;

        return $this;
    }

    public function getOutput(): ?int
    {
        return $this->output;
    }

    public function setOutput(int $output): static
    {
        $this->output = $output;

        return $this;
    }

    public function getDateTransaction(): ?\DateTimeInterface
    {
        return $this->dateTransaction;
    }

    public function setDateTransaction(\DateTimeInterface $dateTransaction): static
    {
        $this->dateTransaction = $dateTransaction;

        return $this;
    }

    public function getType(): ?string
    {
        return $this->type;
    }

    public function setType(string $type): static
    {
        $this->type = $type;

        return $this;
    }

    public function getSomme(): ?float
    {
        return $this->somme;
    }

    public function setSomme(float $somme): static
    {
        $this->somme = $somme;

        return $this;
    }

    public function getIdEntity(): ?int
    {
        return $this->idEntity;
    }

    public function setIdEntity(?int $idEntity): static
    {
        $this->idEntity = $idEntity;

        return $this;
    }
}
