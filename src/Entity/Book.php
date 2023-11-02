<?php

namespace App\Entity;

use App\Repository\BookRepository;
use Doctrine\DBAL\Types\Types;
use Doctrine\ORM\Mapping as ORM;

#[ORM\Entity(repositoryClass: BookRepository::class)]
class Book
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column(type: 'integer')]
    private ?int $ref = null;

    #[ORM\Column(length: 255)]
    private ?string $title = null;

    #[ORM\Column(length: 255)]
    private ?string $category = null;

    #[ORM\Column(type: Types::DATE_MUTABLE)]
    private ?\DateTimeInterface $Publisheddate= null;

    #[ORM\Column(type: Types::BINARY)]
    private $published = null;

    #[ORM\ManyToOne(inversedBy: 'book')]
    private ?Author $author = null;

    public function getRef(): ?int
    {
        return $this->ref;
    }

    public function getTitle(): ?string
    {
        return $this->title;
    }

    public function setTitle(string $title): static
    {
        $this->title = $title;

        return $this;
    }

    public function getCategory(): ?string
    {
        return $this->category;
    }

    public function setCategory(string $category): static
    {
        $this->category = $category;

        return $this;
    }

    public function getPublisheddate(): ?\DateTimeInterface
    {
        return $this->Publisheddate;
    }

    public function setPublisheddate(\DateTimeInterface $Publisheddate): static
    {
        $this->Publisheddate = $Publisheddate;

        return $this;
    }

    public function getPublished()
    {
        return $this->published;
    }

    public function setPublished($published): static
    {
        $this->published = $published;

        return $this;
    }

    public function getAuthor(): ?Author
    {
        return $this->author;
    }

    public function setAuthor(?Author $author): static
    {
        $this->author = $author;

        return $this;
    }
    public function __toString(): string
{
    if ($this->Publisheddate) {
        return $this->title . ' (' . $this->Publisheddate->format('Y-m-d') . ')';
    }

    // Handle the case when Publisheddate is null
    return $this->title . ' (Publication date not available)';
}


                                                                                                                              
}
