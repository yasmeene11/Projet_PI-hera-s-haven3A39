<?php

namespace App\Form;

use App\Entity\Animal;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;
use Symfony\Component\Form\Extension\Core\Type\FileType;
use Symfony\Component\Form\DataTransformerInterface;
use Symfony\Component\Form\Exception\TransformationFailedException;

class AnimalType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder
            ->add('Animal_Name')
            ->add('Animal_Breed')
            ->add('Animal_Status')
            ->add('Animal_Type')
            ->add('Age')
            ->add('Enrollement_Date')
            ->add('Animal_Image', FileType::class, [
                'label' => 'Animal Image',
                'required' => false,
            ]);

        $builder->get('Animal_Image')->addModelTransformer(new class() implements DataTransformerInterface {
            public function transform($value)
            {
                // Transform the File instance to a string (file name)
                if ($value instanceof \Symfony\Component\HttpFoundation\File\File) {
                    return $value->getFilename();
                }

                return null; // Adjust this based on your requirements
            }

            public function reverseTransform($value)
            {
                // Reverse transform the string (file name) to a File instance
                if ($value instanceof \Symfony\Component\HttpFoundation\File\UploadedFile) {
                    return $value;
                }

                if (is_string($value)) {
                    // Your logic to create a File instance, e.g., using the kernel.project_dir
                    // and the actual upload directory
                    $uploadDirectory = $this->getParameter('kernel.project_dir') . '/public/animal_images/';
                    $filePath = $uploadDirectory . $value;

                    if (file_exists($filePath)) {
                        return new \Symfony\Component\HttpFoundation\File\File($filePath);
                    }

                    throw new TransformationFailedException("File not found: $value");
                }

                return null;
            }
        });
    }

    public function configureOptions(OptionsResolver $resolver): void
    {
        $resolver->setDefaults([
            'data_class' => Animal::class,
        ]);
    }
}